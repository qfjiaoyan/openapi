package com.qianfeng.openapi.web.master.shiro;

import com.qianfeng.openapi.web.master.dao.MenuDAO;
import com.qianfeng.openapi.web.master.pojo.Menu;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    public static final String ROLE_STRING = "roles[{0}]";
    private MenuDAO menuDAO;

    public void setMenuDAO(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    private static String filterChainDefinitions;
    @Override
    public void setFilterChainDefinitions(String definitions) {
        filterChainDefinitions=definitions;
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection("urls");
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection("");
        }

        List<Menu> menus=menuDAO.getAllMenu();
        for (Menu menu : menus) {
            if(StringUtils.hasText(menu.getUrl())){//如果地址不等于空
                //把菜单对应的角色id存到shiro中
                //  index.html=roles[1,2,3]
                List<Integer> roleIds=menuDAO.getMenuRoleId(menu.getId());//可以访问当前菜单的角色id
                StringBuilder builder=new StringBuilder();
                if(roleIds!=null&&roleIds.size()>0){
                    for (Integer roleId : roleIds) {
                        builder.append(roleId).append(",");
                    }
                    String str=builder.substring(0,builder.length()-1);//最后一个逗号去掉
                    section.put(menu.getUrl(), MessageFormat.format(ROLE_STRING, str));
                }
            }
        }
        section.put("/**","authc");//放在最后，其它的地址只要登陆了就能访问
        this.setFilterChainDefinitionMap(section);
    }

    /**
     * 当重新给角色授权时使用，因为菜单的权限在项目启动的时候就加载了，所以后追加的权限需要再次追加进来
     */
    public synchronized void update() {
        try {
            AbstractShiroFilter shiroFilter = (AbstractShiroFilter) this.getObject();
            PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            // 过滤管理器
            DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
            // 清除权限配置
            manager.getFilterChains().clear();
            this.getFilterChainDefinitionMap().clear();

            // 重新设置权限
            this.setFilterChainDefinitions(filterChainDefinitions);


            Map<String, String> chains = this.getFilterChainDefinitionMap();
            if (!CollectionUtils.isEmpty(chains)) {
                Iterator var12 = chains.entrySet().iterator();

                while (var12.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry) var12.next();
                    String url = (String) entry.getKey();
                    String chainDefinition = (String) entry.getValue();
                    manager.createChain(url, chainDefinition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
