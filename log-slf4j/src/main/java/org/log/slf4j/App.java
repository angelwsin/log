package org.log.slf4j;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.subst.Parser;
import ch.qos.logback.core.subst.Token;
import ch.qos.logback.core.subst.Tokenizer;
import ch.qos.logback.core.util.OptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.PatternLayout;
import org.slf4j.impl.StaticLoggerBinder;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;

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

		//解析属性
		LoggerContext context = (LoggerContext)StaticLoggerBinder.getSingleton().getLoggerFactory();
		context.putProperty("jdls","98989");
		context.putProperty("level","level:${jdls}");
		context.putProperty("jdls:levelx","89438493");
		context.putProperty("xxxx","${level:-info}");
		context.putProperty("xxxxyy","${jdls:levelx:-info:${jdls}}");//-后面跳过包含前面的:

		context.putProperty("xyy","${jdls:levelx:${jdls}}");//不能解析 jdls:levelx



		String stx = OptionHelper.substVars("${jdls}09090",context,context);
		String st = OptionHelper.substVars("${jdls:levelx}09090",context,context);
		String sts = OptionHelper.substVars("${level}09090",context,context);
		String stsxx = OptionHelper.substVars("${level}:09090",context,context);
		String sx = OptionHelper.substVars("${xxxx}:09090",context,context);

		String sy = OptionHelper.substVars("${xxxxyy}:09090",context,context);
		String xxy = OptionHelper.substVars("${xyy}:09090",context,context);



		//解析规则   ${xxxx}  填充为对应属性 可以嵌套
		// 特殊字符的解析 : 后面如果是 $ 直接解析为属性


		System.out.println(st);
		System.out.println(sts);
		System.out.println(stsxx);
		System.out.println(sx);
		System.out.println(sy);
		System.out.println(xxy);

	}
}
