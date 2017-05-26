package org.skynet.bi.pub.obj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.skynet.bi.pub.obj.po.BIObjectDependPO;

/**
 * 对象依赖Mapper
 * @author javadebug
 *
 */
@Mapper
public interface BIObjectDependMapper {
	static class FIELDS {
		static final String INSERT_FIELDS = "id, tenant_id, prior_id, fellow_id";
		static final String SELECT_FIELDS = "id as id, tenant_id as tenantId, prior_id as priorId, fellowId as fellowId";
	}
	
	@Insert("insert into realform.ri_object_depend_on (" + FIELDS.INSERT_FIELDS + ") values("
			+ "#{id},#{tenant_id},#{prior_id},#{fellow_id}")
	public void create(BIObjectDependPO depend);
	
	@Select("SELECT " + FIELDS.SELECT_FIELDS + "( from realform.ri_object_depend_on where follow_id=#{id}") 
    public List<BIObjectDependPO> getPirors(@Param("id") long id);
	
	@Select("SELECT " + FIELDS.SELECT_FIELDS + " from realform.ri_object_depend_on where prior_id=#{id}") 
    public List<BIObjectDependPO> getFollows(@Param("id") long id);
	
	@SelectProvider(type=BIObjectDefineSqlProvider.class,method="deleteByIds")
	public void deleteByIds(@Param("ids") long[] ids);
}
