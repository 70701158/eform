package org.skynet.bi.pub.obj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.skynet.bi.pub.obj.po.BIObjectDefinePO;

/**
 * BI对象Mapper
 * @author javadebug
 *
 */
@Mapper
public interface BIObjectDefineMapper {
	static class FIELDS {
		static final String SELECT_FIELDS = "id as id, tenant_id as tenantId, define as define";
	}
	
	@Select("SELECT " + FIELDS.SELECT_FIELDS + " from realform.ri_object_define where id=#{id}") 
    public BIObjectDefinePO get(@Param("id") long id);
	
	@SelectProvider(type=BIObjectDefineSqlProvider.class,method="getByIds")
	public List<BIObjectDefinePO> getByIds(@Param("ids") Long[] ids);
	
	/**
	 * 插入新的对象
	 * @param obj
	 */
	@Insert("INSERT INTO realform.ri_object_define(id, tenant_id, define) VALUES (#{id}, #{tenantId}, #{define})")
	public void create(BIObjectDefinePO obj);
	
	/**
	 * 更新指定对象
	 * @param id
	 * @param obj
	 */
	@Update("UPDATE realform.ri_object_define SET tenant_id=#{tenantId}, define=#{define} where id=#{id}")
	public void update(BIObjectDefinePO obj);
	
//	@Delete("delete from realform.ri_object_define where id=#{id}")
//	public void delete(long id);
	
	@SelectProvider(type=BIObjectDefineSqlProvider.class,method="deleteByIds")
	public void deleteByIds(long[] ids);
}
