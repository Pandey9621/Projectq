/*********************** Ideabytes Software India Pvt Ltd *********************                                   
 * UserApplicationsRepository Interface representing a repository for managing entities of a UserApplicationsEntity type.
 * @param <UserApplicationsEntity> The type of entities managed by this repository.
 * @author  Chandan Pandey
 * @version 20.0.1
 * @since   2023-07-5.
 */

package com.ideabytes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ideabytes.binding.UserApplicationEntity;

@Repository
public interface UserApplicationsRepository extends JpaRepository<UserApplicationEntity, Integer> {
	public List<UserApplicationEntity> findByUserId(int id);

//	public UserApplicationEntity save(UserApplicationEntity ube);

	int deleteByAppIdAndUserId(int appId, int userId);

	public UserApplicationEntity findByUserIdAndAppId(int userId, int clientId);

	 int deleteByAppId(int appId);
	 public UserApplicationEntity findByAppId(int id);
	
	@Query(value ="SELECT user_applications.user_id, user_applications.app_id, devices_users.device_id " +
  "FROM user_applications " +
  "INNER JOIN devices_users ON user_applications.user_id = devices_users.user_id", nativeQuery = true)
public String getJoinInformation();

}