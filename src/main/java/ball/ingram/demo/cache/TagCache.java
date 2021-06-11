package ball.ingram.demo.cache;

import ball.ingram.demo.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript","php","javavue","js","css","html5","python","node.js","android","mysql","react.js","ios","golang","docker"));
        tagDTOS.add(program);

        TagDTO frameWork = new TagDTO();
        frameWork.setCategoryName("平台框架");
        frameWork.setTags(Arrays.asList("spring","django","flask","express"));
        tagDTOS.add(frameWork);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("java","android","ios","objective-c","小程序","react-native","swift","xcode","android-studio","flutter","webapp","kotlin"));
        tagDTOS.add(server);

        TagDTO dataSource = new TagDTO();
        dataSource.setCategoryName("奥利给");
        dataSource.setTags(Arrays.asList("mysql","redis","sql数据库","mongodb","json","elasticsearch","nosql","memcached","postgresql","sqlite","mariadb"));
        tagDTOS.add(dataSource);

        TagDTO AI = new TagDTO();
        AI.setCategoryName("希尔德木");
        AI.setTags(Arrays.asList("算法","机器学习","人工智能","深度学习","数据挖掘","tensorflow","神经网络","人脸识别","图像识别","自然语言处理","机器人","pytorch","自动驾驶"));
        tagDTOS.add(AI);

        return tagDTOS;
    }
    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t ->StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;

    }
}
