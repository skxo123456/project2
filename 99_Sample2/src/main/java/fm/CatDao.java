package fm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.Paging;

@Component("cat")
public class CatDao {
	
	private String namespace = "fm.category";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	// ─────────────────────────────────────────────────────────────────────────────
	// 01. 전체조회
	public List<CatBean> getCatList(Paging pageInfo, Map<String,String> map) {
		List<CatBean> list = new ArrayList<CatBean>();
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit());
		list = sqlSessionTemplate.selectList(namespace+".GetCatList",map,rowBounds);
		return list;
	}
	// ─────────────────────────────────────────────────────────────────────────────
	// 02. 전체글갯수
	public int getTotalCount(Map<String,String> map) {
		int cnt = sqlSessionTemplate.selectOne(namespace+".GetTotalCount",map);
		return cnt;
	}
	// ─────────────────────────────────────────────────────────────────────────────
	// 03. 삽입
	public int insertCat(CatBean cat) {
		int cnt = sqlSessionTemplate.insert(namespace+".InsertCat",cat);
		return cnt;
	}
	// ─────────────────────────────────────────────────────────────────────────────
	// 04. 카테고리 가져오기
	public List<RefBean> selectRef() {
		List<RefBean> list = sqlSessionTemplate.selectList(namespace+".SelectRef");
		return list;
	}
	// ─────────────────────────────────────────────────────────────────────────────
	// 05. 상세보기
	public CatBean detailCat(int num) {
		CatBean cat = sqlSessionTemplate.selectOne(namespace+".DetailCat",num);
		return cat;
	}
	// ─────────────────────────────────────────────────────────────────────────────

}
