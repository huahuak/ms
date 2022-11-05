package top.moflowerlkh.msg.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.moflowerlkh.msg.dao.po.goods.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}