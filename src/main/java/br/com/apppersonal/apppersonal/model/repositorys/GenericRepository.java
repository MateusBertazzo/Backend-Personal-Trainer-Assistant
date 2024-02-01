//package br.com.apppersonal.apppersonal.model.repositorys;
//
//
//import jakarta.persistence.EntityManager;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.NoRepositoryBean;
//
//import java.io.Serializable;
//import java.util.List;
//
//@NoRepositoryBean
//public interface GenericRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
//    T findByIdAndNotDeleted(ID id);
//
//    List<T> findAllNotDeleted();
//}
