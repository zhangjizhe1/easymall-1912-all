package cn.tedu.search.controller;

import cn.tedu.search.service.IndexService;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    @RequestMapping("/search/manage/query")
    public List<Product> search(@RequestParam("query") String text, Integer page, Integer rows){
        return indexService.search(text,page,rows);
    }
}
