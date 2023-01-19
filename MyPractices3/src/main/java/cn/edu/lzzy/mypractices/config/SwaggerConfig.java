package cn.edu.lzzy.mypractices.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author lzzy on 2022/1/10.
 * Description:
 */
//配置类注解
@Configuration
//开启swagger自定义接口文档
@EnableOpenApi
public class SwaggerConfig {
     //用于读取配置文件 application.properties 中 swagger 属性是否开启
    @Value("${swagger.enabled}")
    Boolean swaggerEnabled;
    @Bean
    public Docket docket() {
        //返回文档插件对象，使用3.0版本
        return new Docket(DocumentationType.OAS_30)
                //使用自定义swagger信息，apiInfo()在当前类中定义
                .apiInfo(apiInfo())
                //是否启用swagger
                .enable(swaggerEnabled)
                //设置接口扫描
                .select()
                // 接口扫描过滤条件，扫描指定路径下的文件
                .apis(RequestHandlerSelectors.basePackage("cn.edu.lzzy.mypractices.web"))
                // 指定路径处理，PathSelectors.any()代表不过滤任何路径
                //.paths(PathSelectors.any())
                .build();
    }
    //API基础信息定义，替换swagger页面默认信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Practices接口文档")
                .description("本接口适用于软件技术专业web及uni-app课程教学")
                .contact(new Contact("柳州职业技术学院", "http://www.lzzy.edu.cn", "admin@lzzy.net"))
                .version("V1.0")
                .build();
    }
}
