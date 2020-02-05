package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.repo.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
