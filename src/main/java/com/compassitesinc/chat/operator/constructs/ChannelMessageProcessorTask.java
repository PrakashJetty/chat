package com.compassitesinc.chat.operator.constructs;

import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Created by prakashjetty on 11/15/18.
 */
public class ChannelMessageProcessorTask extends ProtocolProcessTask {

    private Path rootLocation;

    public ChannelMessageProcessorTask(Path rootLocation) {
        this.rootLocation = rootLocation;
    }

    @Override
    public void processStep(ChannelMessageContext channelMessageContext) {
        if (channelMessageContext.getChannelMessage().getRequestType().equalsIgnoreCase("INIT")) {
            ChannelMessage channelMessage = new ChannelMessage("INITPINGACK", channelMessageContext.getChannelMessage().getFromUserId());
            channelMessageContext.setChannelMessage(channelMessage);

        } else if (channelMessageContext.getChannelMessage().getRequestType().equalsIgnoreCase("GETFILE")) {
            ChannelMessage channelMessage = new ChannelMessage();
            channelMessage.setToUserId(channelMessageContext.getChannelMessage().getFromUserId());
            channelMessage.setChannelFileMessageFragment(channelMessageContext.getChannelMessage().getChannelFileMessageFragment());
            channelMessage.setAccept("file");
            try {
                FileSystemStorageService fileSystemStorageService = new FileSystemStorageService(rootLocation);

                String fileUrl = channelMessageContext.getChannelMessage().getChannelFileMessageFragment().getFilePath();
                String[] uris = fileUrl.split("//");

                Resource resource = fileSystemStorageService.loadAsResource(uris[1]);
                byte[] bytes = new byte[(int) resource.contentLength()];
                channelMessage.getChannelFileMessageFragment().setFileName(resource.getFilename());
                File file = resource.getFile();
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytes);
                channelMessage.getChannelFileMessageFragment().setFileBytesBase64(Base64.getEncoder().encodeToString(bytes));
                channelMessageContext.setChannelMessage(channelMessage);
            } catch (Exception ex) {
                channelMessageContext.setSuccess(false);
                channelMessageContext.getChannelMessage().setMessage(" Could not retreieve file:");
            }
        } else if (channelMessageContext.getChannelMessage().getRequestType().equalsIgnoreCase("MSGACK")) {

            if (channelMessageContext.getChannelMessage().getContentType().startsWith("text"))
                channelMessageContext.getChannelMessage().setMessage("MSGACKTEXT");
            else if (channelMessageContext.getChannelMessage().getContentType().startsWith("file"))
                channelMessageContext.getChannelMessage().setMessage("MSGACKFILE");
        }
//        UserIdLastActiveTimestampMap.getLastActiveTimestampMap()
//                .put(channelMessageContext.getChannelMessage().getFromUserId(),
//                        System.currentTimeMillis());


        channelMessageContext.setSuccess(true);
    }
}
