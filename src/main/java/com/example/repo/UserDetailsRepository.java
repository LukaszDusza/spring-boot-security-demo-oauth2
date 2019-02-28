package com.example.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.UserInfo;

@Repository
@Transactional
public interface UserDetailsRepository extends JpaRepository<UserInfo, Integer> {

	 UserInfo findByUserNameAndEnabled(String userName, short enabled);

	 List<UserInfo> findAllByEnabled(short enabled);

	 UserInfo findById(Integer id);

//	@Override
//	 UserInfo save(UserInfo userInfo);

	 void deleteById(Integer id);
}
