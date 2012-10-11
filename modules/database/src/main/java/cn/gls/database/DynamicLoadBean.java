package cn.gls.database;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @ClassName: DynamicLoadBean.java
 * @Description  动态加载bean类
 * @Date  2012-7-5
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-7-5
 */
public class DynamicLoadBean implements ApplicationContextAware {

	private ConfigurableApplicationContext applicationContext = null;
	
	public ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(
			ApplicationContext applicationContext) {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}
	 /** 
     * 向spring的beanFactory动态地装载bean 
     * @param configLocationString 要装载的bean所在的xml配置文件位置。 
         spring配置中的contextConfigLocation，同样支持诸如"/WEB-INF/ApplicationContext-*.xml"的写法。 
     */  
    public void loadBean(String configLocationString){  
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry)getApplicationContext().getBeanFactory());  
        beanDefinitionReader.setResourceLoader(getApplicationContext());  
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));  
        try {  
            String[] configLocations = new String[]{configLocationString};  
            for(int i=0;i<configLocations.length;i++)  
                beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(configLocations[i]));
                
           // beanDefinitionReader.
        } catch (BeansException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    } 
    public void unloadBean(){
           
    //    applicationContext.
        DefaultListableBeanFactory listBeanFactory=(DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//        listBeanFactory.removeBeanDefinition("glsContextLoader");
        listBeanFactory.removeBeanDefinition("segAddressEngine");
        listBeanFactory.removeBeanDefinition("autoSegWord");
        listBeanFactory.removeBeanDefinition("addressOperator");
  // BeanDefinition
        //  getApplicationContext().getBeanFactory().
    }

}
