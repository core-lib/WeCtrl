package org.qfox.wectrl.service.weixin.cgi_bin;

import java.util.List;

/**
 * <p>
 * Description:含有二级按钮的复杂按钮
 * </p>
 * 
 * <p>
 * Company: 广州市俏狐信息科技有限公司
 * </p>
 * 
 * @author zbl 727513242@qq.com
 *
 * @date 2016年7月24日
 *
 */
public class ComplexButton extends Button {

	private List<Button> sub_button;
	
	public List<Button> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}
}
