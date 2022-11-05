package top.moflowerlkh.msg.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.moflowerlkh.msg.dao.po.goods.GoodsOrder;

public interface GoodsOrderRepository extends JpaRepository<GoodsOrder, Long> {
}