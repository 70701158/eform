package org.skynet.bi.pub.obj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.skynet.bi.pub.obj.po.BIObjectSummaryPO;

/**
 * BI对象Mapper
 * @author javadebug
 *
 */
@Mapper
public interface BIObjectSummaryMapper {
	static class FIELDS {
		static final String SELECT_FIELDS = "id as id, tenant_id as tenantId, parent_id as parentId, name as name, type as type, "
				+ "origin_thumbnail_url as originThumbnail, middle_thumbnail_url as middleThumbnailUrl, small_thumbnail_url as smallThumbnailUrl, "
				+ "custom_icon_url as customIconUrl, deleted as deleted, create_time as createTime, creator as creator, "
				+ "last_modified as lastModified, last_modifier as lastModifier";
	}
	
	@Select("SELECT " + FIELDS.SELECT_FIELDS + " from realform.ri_object_summary where id=#{id}") 
    public BIObjectSummaryPO get(@Param("id") long id);
	
	@SelectProvider(type=BIObjectSummarySqlProvider.class,method="getByIds")
	public List<BIObjectSummaryPO> getByIds(@Param("ids") Long[] ids);
	
	@Select("SELECT " + FIELDS.SELECT_FIELDS + " from realform.ri_object_summary where creator=#{userId} and (parent_id is null or parent_id=0)") 
	public List<BIObjectSummaryPO> getByUser(@Param("userId") long userId);
	
	/**
	 * 获得指定的子对象
	 * @param id
	 * @return
	 */
	@Select("SELECT " + FIELDS.SELECT_FIELDS + " from realform.ri_object_summary where parent_id=#{parentId} order by create_time, last_modified") 
	public List<BIObjectSummaryPO> getChildren(@Param("parentId") long parentId);
	
	/**
	 * 获得指定的对象和子对象
	 * @param parentId
	 * @return
	 */
	@Select("SELECT " + FIELDS.SELECT_FIELDS + " from realform.ri_object_summary "
			+ "where id=#{id} or parent_id=#{id} order by create_time, last_modified") 
	public List<BIObjectSummaryPO> getWithChildren(@Param("id") long id);
	
	/**
	 * 插入新的对象
	 * @param obj
	 */
	@Insert("INSERT INTO realform.ri_object_summary(id, tenant_id, parent_id, name, type, "
			+ "origin_thumbnail_url, middle_thumbnail_url, small_thumbnail_url, custom_icon_url, deleted, "
			+ "create_time, creator, last_modified, last_modifier) "
			+ "VALUES (#{id}, #{tenantId}, #{parentId}, #{name}, #{type}, "
			+ "#{originThumbnailUrl}, #{middleThumbnailUrl}, #{smallThumbnailUrl}, #{customIconUrl}, #{deleted}, "
			+ "#{createTime}, #{creator}, #{lastModified}, #{lastModifier})")
	public int create(BIObjectSummaryPO obj);
	
	/**
	 * 更新指定对象
	 * @param id
	 * @param obj
	 */
	@Update("UPDATE realform.ri_object_summary SET tenant_id=#{tenantId}, parent_id=#{parentId}, name=#{name}, type=#{type}, "
			+ "origin_thumbnail_url=#{originThumbnailUrl}, middle_thumbnail_url=#{middleThumbnailUrl}, "
			+ "small_thumbnail_url=#{smallThumbnailUrl}, custom_icon_url=#{customIconUrl}, deleted=#{deleted}, "
			+ "last_modified=#{lastModified}, last_modifier=#{lastModifier} "
			+ "where id=#{id}")
	public void update(BIObjectSummaryPO obj);
	
	@Delete("update realform.ri_object_summary set deleted=true where id=#{id}")
	public void delete(long id);
	
	/**
	 * 删除多条记录
	 * @param ids
	 */
	@SelectProvider(type=BIObjectSummarySqlProvider.class,method="deleteByIds")
	public void deleteByPrimaryKies(long[] ids);
	
}
