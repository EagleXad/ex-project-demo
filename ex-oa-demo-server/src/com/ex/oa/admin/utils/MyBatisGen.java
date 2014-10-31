package com.ex.oa.admin.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/** 
* @ClassName: MyBatisGen 
* @Description: TODO(...) 
* @author Aloneter
* @date 2014-9-27 下午3:00:30 
* @Version 1.0
* 
*/
public class MyBatisGen {

	public static void main(String[] args) {

		String xmlFileString = "WebRoot/WEB-INF/config/generatorConfig.xml";

		try {
			List<String> warnings = new ArrayList<String>();

			boolean overwrite = true;
			File configFile = new File(xmlFileString);

			ConfigurationParser cParser = new ConfigurationParser(warnings);

			Configuration configuration = cParser.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);

			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
			myBatisGenerator.generate(null);

			System.out.println("mybatis gen ########################## OK");

		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("mybatis gen ########################## ERROR");
		}
	}

}
