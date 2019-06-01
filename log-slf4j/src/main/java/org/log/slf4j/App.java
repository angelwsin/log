package org.log.slf4j;

import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.subst.Parser;
import ch.qos.logback.core.subst.Token;
import ch.qos.logback.core.subst.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.PatternLayout;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

/**
 * 
 *
 */
public class App 
{
    static {
    	PatternLayout.defaultConverterMap.put("traceId", TraceIdConverter.class.getName());
	}
	static Logger logger = LoggerFactory.getLogger(App.class);
	
	
	
    public static void main( String[] args )throws Exception
    {
    	
        /**
         * 使用 org.slf4j.impl.StaticLoggerBinder 绑定到不同的实现日志框架
         * 
         * 如：logback
         * 
         * 静态绑定Logger
         * 
         * ch.qos.logback.classic.joran.JoranConfigurator
         * 解析xml配置文件
         * 
         */
    	System.out.println((Integer.MAX_VALUE));
    	long x = System.currentTimeMillis();
    	logger.info((System.currentTimeMillis()-x)+"ms");
    	
    	/**
    	 * logger日志的打印
    	 * 构造成一个ILoggingEvent 时间
    	 * 
    	 * 从this logger开始遍历 查看additive 是否传递
    	 * 
    	 * for (Logger l = this; l != null; l = l.parent) {
            writes += l.appendLoopOnAppenders(event);
            if (!l.additive) {
                break;
            }
        }
         ch.qos.logback.core.AppenderBase.doAppend(E) 不同类型的appenders
    	 */

		//new JoranConfigurator().doConfigure("logback.xml");
		//配置文件变量解析
		//ch.qos.logback.core.subst.NodeToStringTransformer.substituteVariable
		Tokenizer tokenizer = new Tokenizer("${jdls}09090");
		Method m = Tokenizer.class.getDeclaredMethod("tokenize");
		m.setAccessible(true);
		List<Token> tokens = (List<Token>)m.invoke(tokenizer);
		//List<Token> tokens = tokenizer.tokenize();
		Parser parser = new Parser(tokens);
		parser.parse();
    }
}
