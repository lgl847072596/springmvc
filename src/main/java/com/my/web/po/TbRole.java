package com.my.web.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Tb_Role")
public class TbRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int roleId;
	@Column
	private int roleLevel;
	@Column
	private String roleDesc;

	public TbRole() {
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Override
	public String toString() {
		return "TbRole [roleId=" + roleId + ", roleLevel=" + roleLevel + ", roleDesc=" + roleDesc + "]";
	}

}
