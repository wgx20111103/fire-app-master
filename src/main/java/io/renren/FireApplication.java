
package io.renren;

import io.renren.common.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"io.renren"})
@ServletComponentScan
@SpringBootApplication(scanBasePackages = "io.renren")
public class FireApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FireApplication.class, args);
		SpringUtil.setApplicationContext(context);

	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		resolver.setDefaultLocale(Locale.CHINA);
		resolver.setSupportedLocales(Arrays.asList(Locale.US, Locale.CHINA,new Locale("ru","RUS")));
		return resolver;
	}

}