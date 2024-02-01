//package br.com.apppersonal.apppersonal.service;
//
//import br.com.apppersonal.apppersonal.model.repositorys.GenericRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
//
//import java.io.Serializable;
//import java.util.List;
//
//public abstract class GenericService<T, ID extends Serializable> implements GenericRepository<T, ID> {
//
//    private final Class<T> entityClass;
//
//    private final EntityManager entityManager;
//
//    public GenericService(Class<T> entityClass, EntityManager entityManager) {
//        this.entityClass = entityClass;
//        this.entityManager = entityManager;
//    }
//
//    @Override
//    public T findByIdAndNotDeleted(ID id) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
//        Root<T> root = criteriaQuery.from(entityClass);
//
//        criteriaQuery.select(root).where(
//                criteriaBuilder.and(
//                        criteriaBuilder.equal(root.get("id"), id),
//                        criteriaBuilder.equal(root.get("deleted"), false)
//                )
//        );
//
//        return entityManager.createQuery(criteriaQuery).getSingleResult();
//    }
//
//    @Override
//    public List<T> findAllNotDeleted() {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
//        Root<T> root = criteriaQuery.from(entityClass);
//
//        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("deleted"), false));
//
//        return entityManager.createQuery(criteriaQuery).getResultList();
//    }
//}
