package web.data.repogitory;

import org.springframework.data.jpa.repository.JpaRepository;

import web.data.entity.WishPhoto;
import web.data.entity.WishPhotoPK;

public interface WishPhotoRepo extends JpaRepository<WishPhoto, WishPhotoPK>{
}
