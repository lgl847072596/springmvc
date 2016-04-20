package com.my.web.tool;
/**
 * @description 字符校验
 * @author guilin
 *
 */
public class CharacterTool {
	
	private CharacterTool(){}
	/**
	 * @description 字符串是null或空
	 * @param target
	 * @return
	 */
	public static boolean isNullOrEmpty(String target){
		return (target==null||target.length()<1);
	}
	

}
