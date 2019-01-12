package com.ly.item.web;/**
 * Create By IvanLee on 2018/12/17
 */

import com.ly.common.vo.ExceptionResult;
import com.ly.item.service.BrandService;
import com.ly.common.vo.PageResult;
import com.ly.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@ClassName BrandController
 *@Description Todo
 *@Author Lee
 *@Date 2018/12/17 22:28
 *@Version 1.0
 **/
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResult<Brand> result = this.brandService.queryBrandByPageAndSort(page,rows,sortBy,desc, key);
        if (result == null || result.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 品牌的新增
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(@RequestParam("cids")List<Long> cids,Brand brand) {
        this.brandService.saveBrand(cids, brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    /**
     *  品牌的修改
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(@RequestParam(value = "cids") List<Long> cids, Brand brand) {
        this.brandService.updateBrand(cids, brand);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    /**
     * 品牌的删除
     * @param bid
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteBrand(@RequestParam(value = "id")Long bid){
        this.brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 根据分类的cid查出所有的品牌
     * @param cid
     * @return
     */
    @GetMapping("cid/{id}")
    public ResponseEntity<List<Brand>> queryBrandsByCategoryId(@PathVariable(value = "id")Long cid
    ){
        List<Brand> list = this.brandService.queryBrandsByCategoryId(cid);
        if (list==null||list.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * 根据品牌id的集合查询所有的品牌
     * @param brandIds
     * @return
     */
    @GetMapping("bids")
    public ResponseEntity<List<Brand>> queryBrandsByBrandIds(@RequestParam("bids") List<Long> brandIds){
        List<Brand> brands = this.brandService.queryBrandsByBids(brandIds);
        if (brands==null||brands.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(brands);
    }

    /**
     * 根据商品品牌ID查询品牌
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandService.queryBrandByBid(id));
    }

    /**
     * 根据ids查询品牌
     * @param ids
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandsByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(brandService.queryBrandsByBids(ids));
    }
}
