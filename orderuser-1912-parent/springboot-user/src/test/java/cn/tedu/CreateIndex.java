package cn.tedu;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateIndex {
    @Test
    public void createIndex() throws Exception {
        //指定一个文件夹 d:/index01
        Path path = Paths.get("d:/index01");
        //使用lucene的directory读取path
        FSDirectory dir = FSDirectory.open(path);
        //创建一个输出流对象writer,输出的同时会计算分词,并且可指定文件重名
        //是覆盖数据还是追加数据
        Analyzer ik=new IKAnalyzer6x();
        IndexWriterConfig config=new IndexWriterConfig(ik);
        //指定打开索引文件位置path的方式 CREATE 无则创建有的覆盖
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        //利用这个配置对象创建输出流
        IndexWriter writer=new IndexWriter(dir,config);
        //直接输出,没有document数据,导致空索引文件创建
        //准备一些document数据
        Document doc1=new Document();
        Document doc2=new Document();
        //测试案例内容为准 lucene使用add方法想document中添加各种属性
        doc1.add(new TextField("title","美国耍赖",
                Field.Store.YES));//title,content,publisher,click
        doc1.add(new TextField("content",
                "频繁甩锅,控诉中国疫情信息透露不明确", Field.Store.YES));
        doc1.add(new TextField("publisher","新华网", Field.Store.YES));
        doc1.add(new IntPoint("click",58));
        doc1.add(new StringField("click","58次", Field.Store.YES));
        //field类型有哪些,什么作用,Store.YES/NO区别
        doc2.add(new TextField("title","美国甩锅",
                Field.Store.YES));
        doc2.add(new StringField("content","美国谎称,援助中国1亿美金,中国外交部称从未收到",
                Field.Store.YES));
        //都是字符串 TextField和StringField什么区别
        doc2.add(new TextField("publisher","新华网", Field.Store.YES));
        doc2.add(new IntPoint("click",66));
        doc2.add(new StringField("click","66次", Field.Store.YES));
        //writer携带document数据输出到索引文件
       /* doc2.add(new IntPoint("disk",10240000000));
        doc2.add(new StringField("disk","10G"));*/
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.commit();
    }

}
