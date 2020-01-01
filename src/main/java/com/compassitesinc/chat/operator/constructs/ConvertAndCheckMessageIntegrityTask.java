package com.compassitesinc.chat.operator.constructs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by prakashjetty on 11/15/18.
 */
public class ConvertAndCheckMessageIntegrityTask extends ProtocolProcessTask {
    private static final Logger logger = LoggerFactory.getLogger(ConvertAndCheckMessageIntegrityTask.class);

    private Path rootLocation;

    public ConvertAndCheckMessageIntegrityTask(Path rootLocation) {
        this.rootLocation = rootLocation;
    }

    @Override
    public void processStep(ChannelMessageContext channelMessageContext) {

        try {
            if (channelMessageContext.getChannelMessage() != null && channelMessageContext.getChannelMessage().getRequestType() != null && channelMessageContext.getChannelMessage().getRequestType().equalsIgnoreCase("SENDFILE") && channelMessageContext.getFileDirectory() != null) {

                File sfile = new File(channelMessageContext.getFileDirectory());

                byte[] filebytes = Files.readAllBytes(Paths.get(channelMessageContext.getFileDirectory()));

                String jsonString = new String(Arrays.copyOfRange(filebytes, 11, filebytes.length - 9), "UTF-8");
//                System.out.println(jsonString);
                channelMessageContext.setChannelMessage(ChatCommMessageEncoder.deserialize(jsonString));
                String contentType = channelMessageContext.getChannelMessage().getContentType();
                String[] contenctTypeParts = null;
                if (contentType != null) {
                    contenctTypeParts = contentType.split("::");
                    if (contenctTypeParts.length != 2) {
                        channelMessageContext.setSuccess(true);
                        logger.debug(" Invalid content Type..");
                        channelMessageContext.setStatsuMessage("Invalid Content Type");
                        channelMessageContext.getChannelMessage().setToUserId(channelMessageContext.getChannelMessage().getFromUserId());
                        channelMessageContext.getChannelMessage().setMessage("Message Corrupted");

                    }
                }
                if (contenctTypeParts != null && contenctTypeParts.length == 2 && contenctTypeParts[0].equalsIgnoreCase("file") && channelMessageContext.getChannelMessage().getRequestType().equalsIgnoreCase("SENDFILE")) {
                    ChannelFileMessageFragment channelFileMessageFragment = channelMessageContext.getChannelMessage().getChannelFileMessageFragment();

                    String base64String = channelFileMessageFragment.getFileBytesBase64();
                    byte[] bytes = Base64.getDecoder().decode(base64String);
                    try {
                        FileSystemStorageService fileSystemStorageService = new FileSystemStorageService(this.rootLocation);
                        File tfile = new File(channelFileMessageFragment.getFileName());
                        FileOutputStream fileOutputStream = new FileOutputStream(tfile);
                        fileOutputStream.write(bytes);
                        Path path = fileSystemStorageService.storeFile(tfile, channelMessageContext.getChannelMessage().getFromUserId() + "/" + UUID.randomUUID().toString());
                        ChannelFileMessageFragment channelFileMessageFragmentRes = new ChannelFileMessageFragment();
                        channelFileMessageFragmentRes.setFilePath("file://" + path.toString());
                        ChannelMessage channelMessage = new ChannelMessage();
                        channelMessage.setToUserId(channelMessageContext.getChannelMessage().getToUserId());
                        channelMessage.setFromUserId(channelMessageContext.getChannelMessage().getFromUserId());
                        channelMessage.setAccept("fileUrl");
                        channelMessage.setChannelFileMessageFragment(channelFileMessageFragmentRes);
                        channelMessageContext.setChannelMessage(channelMessage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        channelMessageContext.setSuccess(false);
                        channelMessageContext.getChannelMessage().setToUserId(channelMessageContext.getChannelMessage().getFromUserId());
                        channelMessageContext.getChannelMessage().setMessage("Message Corrupted");
                    }
                } else {
//                ChannelMessage channelMessage = ChatCommMessageEncoder
//                        .deserialize(new String(channelMessageContext.getBytes(), "UTF-8").trim());
//                channelMessageContext.setChannelMessage(channelMessage);
                    channelMessageContext.setSuccess(true);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            channelMessageContext.setChannelMessage(new ChannelMessage("Message Corrupted", null, null));
            channelMessageContext.getChannelMessage().setToUserId(channelMessageContext.getChannelMessage().getFromUserId());

            channelMessageContext.setSuccess(false);
        }
    }
}
