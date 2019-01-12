package com.ly.search.pojo;

import com.ly.common.vo.PageResult;
import com.ly.item.pojo.Brand;
import com.ly.item.pojo.Category;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lee
 * @date 2018/12/23
 */
@Data
public class SearchResult<Goods> extends PageResult<Goods> {

    private List<Brand> brands;
    private List<Category> categories;
    //规格参数过滤条件
    private List<Map<String, Object>> specs;

    public SearchResult(Long total,
                        Integer totalPage,
                        List<Goods> items,
                        List<Category> categories,
                        List<Brand> brands,
                        List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SearchResult<?> that = (SearchResult<?>) o;
        return Objects.equals(brands, that.brands) &&
                Objects.equals(categories, that.categories) &&
                Objects.equals(specs, that.specs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), brands, categories, specs);
    }
}
