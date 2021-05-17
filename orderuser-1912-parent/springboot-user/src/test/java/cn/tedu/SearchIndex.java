package cn.tedu;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchIndex {
    @Test
    public void search() throws Exception {
        Path path = Paths.get("f:/index01");
        FSDirectory dir=FSDirectory.open(path);
        //获取一个输入流reader
        IndexReader reader= DirectoryReader.open(dir);
        //利用输入流创建搜索对象
        IndexSearcher search=new IndexSearcher(reader);
        //准备一个词项查询条件
        Term term=new Term("content","中国");
        Query query=new TermQuery(term);
        //lucene实现浅查询,数据量对于索引文件来讲太大了
        //调用search的方法使用query条件查询一个与分页有关的总数据 page=2 rows=5
        int page=1;
        int rows=2;
        TopDocs topDocs = search.search(query, page*rows);
        //从topDocs中拿到docid值就可以读取需要的数据
        ScoreDoc[] docs=topDocs.scoreDocs;
        //在数组中记录了前10个document的id,计算page=2 rows=5的数据
        for (int i=0;i<docs.length;i++){
            //前5条都不读
            if(i<(page-1)*rows){
                //不是第二页的数据
                continue;
            }else{
                //到当前页数据
                //获取documentid读取索引文件中真正数据
                int docId=docs[i].doc;
                //io读取document数据
                Document doc = search.doc(docId);
                //从中解析document数据
                System.out.println("title:"+doc.get("title"));
                System.out.println("content:"+doc.get("content"));
                System.out.println("publisher:"+doc.get("publisher"));
                System.out.println("click:"+doc.get("click"));
                //存储于不存储的区别,存储的数据都会返回打印,不存储的数据都不会打印控制台
            }
        }
    }

    @Test
    public void multiFieldQuery() throws Exception {
        //指定搜索文件 index01
        Path path = Paths.get("d:/index01");
        FSDirectory dir=FSDirectory.open(path);
        //创建搜索对象
        IndexReader reader=DirectoryReader.open(dir);
        IndexSearcher searcher=new IndexSearcher(reader);
        //生成查询条件 多域查询

        //给定本次查询需要使用的多个域名称
        String[] fields={"title","content"};
        //准备一个解析查询条件的analyzer,最好是为了查询效果,使用创建索引时analyzer
        Analyzer a=new IKAnalyzer6x();
        //创建一个多域查询解析对象parser
        MultiFieldQueryParser parser=new MultiFieldQueryParser(fields,a);
        //利用parser对象对我们的查询条件(一串文本)进行解析才能形成一个多域查询对象
        String keyWord="美国还想要中国怎么样";
        Query query = parser.parse(keyWord);
        //底层keyWord会被分词计算得到一批词项,绑定title和content行程一批TermQuery
        //只要任何一个TermQuery的对象查询到的document都是本次多域查询的结果

        //通过浅查询拿到需要的所有数据
        TopDocs topDocs = searcher.search(query, 5);
        ScoreDoc[] docs=topDocs.scoreDocs;
        //for循环拿到每个ScoreDoc元素,拿到docId读取数据
        for(ScoreDoc scoreDoc:docs){
            int docId=scoreDoc.doc;
            //使用id读取数据
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
        }
    }

    //布尔查询的测试
    @Test
    public void booleanQuery() throws Exception {
        //指定搜索文件 index01
        Path path = Paths.get("F:/新建文本文档");
        FSDirectory dir=FSDirectory.open(path);
        //创建搜索对象
        IndexReader reader=DirectoryReader.open(dir);
        IndexSearcher searcher=new IndexSearcher(reader);
        //不同查询条件构造不同query TODO
        //构造布尔查询子条件
        Query query1=new TermQuery(new Term("title","美国"));
        Query query2=new TermQuery(new Term("content","中国"));
        //封装query1 query2形成布尔子条件
        BooleanClause bc1=new BooleanClause(query1, BooleanClause.Occur.MUST);
        BooleanClause bc2=new BooleanClause(query2, BooleanClause.Occur.MUST_NOT);
        //Occur.MUST,子条件的逻辑关系值
        /*
            MUST:结果集中必须包含该子条件
            MUST_NOT:结果集中必须不能包含该子条件
        */
        //利用子条件 创建出来Query对象
        Query query=new BooleanQuery.Builder().add(bc1).add(bc2).build();
        //通过浅查询拿到需要的所有数据
        TopDocs topDocs = searcher.search(query, 5);
        ScoreDoc[] docs=topDocs.scoreDocs;
        //for循环拿到每个ScoreDoc元素,拿到docId读取数据
        for(ScoreDoc scoreDoc:docs){
            int docId=scoreDoc.doc;
            //使用id读取数据
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
        }
    }
    //范围查询
    @Test
    public void rangeQuery() throws Exception {
        //指定搜索文件 index01
        Path path = Paths.get("F:/新建文本文档");
        FSDirectory dir=FSDirectory.open(path);
        //创建搜索对象
        IndexReader reader=DirectoryReader.open(dir);
        IndexSearcher searcher=new IndexSearcher(reader);
        //不同查询条件构造不同query TODO
        //构造范围查询 click
        Query query = IntPoint.newRangeQuery("click",
                50, 70);
        //通过浅查询拿到需要的所有数据
        TopDocs topDocs = searcher.search(query, 5);
        ScoreDoc[] docs=topDocs.scoreDocs;
        //for循环拿到每个ScoreDoc元素,拿到docId读取数据
        for(ScoreDoc scoreDoc:docs){
            int docId=scoreDoc.doc;
            //使用id读取数据
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
        }
    }
}
