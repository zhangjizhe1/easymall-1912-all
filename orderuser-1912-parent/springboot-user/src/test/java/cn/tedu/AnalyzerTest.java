package cn.tedu;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.StringReader;

public class AnalyzerTest {
    //首先实现一个方法,根据不同的分词器实现类对象
    //对一个文本数据String做流的计算,计算过程中实现分词
    //打印分词文本属性即可
    public void analyze(Analyzer a, String msg){
        try{
            //使用a的分词器,对msg进行分词计算,得到文本打印
            //1 msg转化成流对象
            StringReader reader=new StringReader(msg);
            //2 将reader流调用方法计算成分词流
            TokenStream tokenStream = a.tokenStream("test", reader);
            //3 重置这个流的指针
            tokenStream.reset();
            //获取当前指针位置的二进制文本属性
            CharTermAttribute attribute = tokenStream
                    .getAttribute(CharTermAttribute.class);
            while(tokenStream.incrementToken()){
                System.out.println(attribute.toString());
            }
        }catch (Exception e){
        }

    }
    @Test
    public void run(){
        String msg="中华人民共和国";
        //分词器
        Analyzer a1=new StandardAnalyzer();
        Analyzer a2=new SimpleAnalyzer();
        Analyzer a3=new WhitespaceAnalyzer();
        Analyzer a4=new SmartChineseAnalyzer();
        Analyzer a5=new IKAnalyzer6x();
        System.out.println("**************标准**************");
        analyze(a1,msg);
        System.out.println("**************简单**************");
        analyze(a2,msg);
        System.out.println("**************空格**************");
        analyze(a3,msg);
        System.out.println("**************中文**************");
        analyze(a4,msg);
        System.out.println("**************ik**************");
        analyze(a5,msg);
    }
}
