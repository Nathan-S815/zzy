package com.zzy.core.utils.generate;


import org.mybatis.generator.api.ShellRunner;

public class RunMybatis {

    public static void main(String[] args)  {
        RunMybatis rm = new RunMybatis();
        rm.run(args);
    }

    public void run(String[] args){
        String genXmlPath = this.getClass().getClassLoader().getResource("./mybatis-generator.xml").getPath();
        args = new String[] { "-configfile", genXmlPath, "-overwrite" };
        ShellRunner.main(args);
    }
}

