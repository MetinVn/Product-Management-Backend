package kodlamaio.northwind.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.northwind.entities.concretes.Product;
import kodlamaio.northwind.entities.dtos.ProductWithCategoryDto;

public interface ProductDao extends JpaRepository<Product, Integer> {
    Product getByProductName(String productName);

    Product getByProductNameAndCategory_CategoryId(String productName, int categoryId);

    List<Product> getByProductNameContains(String productName);

    List<Product> getByProductNameStartsWith(String productName);

    @Query("From Product where productName=:productName and category.categoryId=:categoryId")
    List<Product> getByNameAndCategory(String productName, int categoryId);

    @Query("SELECT new kodlamaio.northwind.entities.dtos.ProductWithCategoryDto(p.id, p.productName, p.quantityPerUnit, p.unitPrice, c.categoryName) FROM Product p INNER JOIN p.category c WHERE c.categoryName = :categoryName")
    List<ProductWithCategoryDto> getProductsWithCategoryDetailsByCategoryName(String categoryName);

    @Query("SELECT new kodlamaio.northwind.entities.dtos.ProductWithCategoryDto(p.id, p.productName,p.quantityPerUnit, p.unitPrice, c.categoryName) FROM Product p INNER JOIN p.category c")
    List<ProductWithCategoryDto> getProductsWithCategoryDetails();
}
