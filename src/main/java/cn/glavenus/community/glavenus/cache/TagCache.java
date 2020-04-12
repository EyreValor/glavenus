package cn.glavenus.community.glavenus.cache;

import cn.glavenus.community.glavenus.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creaked by EyreValor on 2020/4/9
 */
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagsDTO = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java","javascript","php","css","html","node.js","python","c++","c"));
        tagsDTO.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","spring boot","struts","laravel","yii","koa","django"));
        tagsDTO.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","tomcat"));
        tagsDTO.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql menmcached","sqlserver","postgresql","sqlite"));
        tagsDTO.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","vim","sublime-text","visual-studio-code","xcode intellij-idea","eclipse","maven","ide","svn"));
        tagsDTO.add(tool);
     return tagsDTO;
    }

    public static String filterInvalid(String tags){
        //将用户的标签值写入数组
        String [] split = StringUtils.split(tags,",");
        //获取标准数组
        List<TagDTO> tagDTOS = get();
        //将标准表转换
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //进行判断并且将错误的值返回
        String invalid = Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}
