package tk.timaca.Reptile.Dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import tk.timaca.Reptile.Bean.Log;
import tk.timaca.Reptile.Bean.ReptileLog;
import tk.timaca.Reptile.Bean.SinaArti;
import tk.timaca.Reptile.Bean.User;

//操作数据库工具类
public class DbUnit {
	
	private static DbUnit dbUnit;
	private static SessionFactory sessionFactory;
	
	private DbUnit(){
		Configuration configuration=new Configuration().configure();//创建配置对象
		ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();//创建服务注册对象
		sessionFactory=configuration.buildSessionFactory(serviceRegistry);//创建会话工厂对象并设置
	}
	
	public static DbUnit getDbUnit(){
		if(dbUnit==null){
			dbUnit=new DbUnit();
		}
		return dbUnit;
	}
        
	
	/**各数据表的CUD
	CRUD是指在做计算处理时的增加(Create)、更新(Update)和删除(Delete)几个单词的首字母简写
	*/
	
	/**
	 * 数据库添加数据操作
	 * @param object
	 * @return
	 */
	public boolean CreateObject(Object object){
		boolean result = false;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try {
			session.save(object);
			transaction.commit();
			result=true;
			
		} catch (Exception e) {
			System.out.println("DbUnit CreateObject 出现异常："+e.toString());
			result=false;
		}finally {
			session.close();
		}
		return result;
	}
	
	/**
	 * 数据库删除数据操作
	 * @param object
	 * @return
	 */
	public boolean DeleteObject(Object object){
		boolean result=false;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try {
			session.delete(object);
			transaction.commit();
			result=true;
		} catch (HibernateException e) {
			System.out.println("DbUnit DeleteObject 出现异常："+e.getMessage());
			result=false;
		}finally {
			session.close();
		}
		return result;
	}
	
	public boolean UpdateObject(Object object){
		boolean result=false;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try {
			session.update(object);
			transaction.commit();
			result=true;
		} catch (HibernateException e) {
			System.out.println("DbUnit UpdateObject 出现异常：");
			e.printStackTrace();
			result=false;
		}finally {
			session.close();
		}
		return result;
	}
	
	/**各数据表的读取查询(Retrieve)
	*/
	
	/**
	 * 根据key在User表查询数据
	 * @param key
	 * @return
	 */
	public User RetrieveUserByKey(String key){
		User user = null;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String hql="from User u where u.key like'%"+key+"%'";
		try {
			Query query=session.createQuery(hql);
			user=(User)query.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("DbUnit RetrieveUserByKey 出现异常：");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return user;
	}
	
	/**
	 * 根据uniquekey在SinaArti表查询数据
	 * @param uniquekey
	 * @return
	 */
	public List<SinaArti> RetrieveSinaArtiByUniquekey(String uniquekey){
		List<SinaArti> sinaArtis=null;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String hql="from SinaArti s where s.uniquekey like '%"+uniquekey+"%'";
		try {
			Query query=session.createQuery(hql);
			sinaArtis=query.list();
		} catch (HibernateException e) {
			System.out.println("DbUnit RetrieveSinaArtiByUniquekey 出现异常：");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return sinaArtis;
	}
	
	/**
	 * 根据title在SinaArti表查询数据
	 * @param title
	 * @return
	 */
	public SinaArti RetrieveSinaArtiByTitle(String title){
		SinaArti sinaArti=null;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String hql="from SinaArti s where s.title like '%"+title+"%'";
		try {
			Query query=session.createQuery(hql);
			sinaArti=(SinaArti)query.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("DbUnit RetrieveSinaArtiByTitle 出现异常：");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return sinaArti;
	}
	
	/**
	 * 统计数据库中的文章
	 * @return
	 */
	public long CountArt(){
		String hql="select count(*) from SinaArti";
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		return (long)session.createQuery(hql).uniqueResult();
		//System.out.println((long)session.createQuery(hql).uniqueResult());
	}
	
	/**
	 * 根据条件在SinaArti查询
	 * @param max 最多查询max条数据
	 * @param first 从first条数据开始查
	 * @return
	 */
	public List<SinaArti> RetrieveSinaArti(int max,int first){
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from SinaArti");
		query.setMaxResults(max);
		query.setFirstResult(first);
		return query.list();
	}
	
         /**
	 * 根据ip在User表查询数据
	 * @param ip
	 * @return
	 */
	public User RetrieveUserByIp(String ip){
		User user = null;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String hql="from User u where u.ip like'%"+ip+"%'";
		try {
			Query query=session.createQuery(hql);
			user=(User)query.list().get(0);
		} catch (HibernateException e) {
			System.out.println("DbUnit RetrieveUserByIp 出现异常：");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return user;
	}
        
         /**
	 * 在User表遍历user
	 * @return
	 */
	public List<User> RetrieveUser(){
		List<User> users = null;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String hql="from User";
		try {
			Query query=session.createQuery(hql);
			users=query.list();
		} catch (HibernateException e) {
			System.out.println("DbUnit RetrieveUser 出现异常：");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return users;
	}
        
         /**
	 * 根据id在User表查询数据
	 * @param id
	 * @return
	 */
        public User RetrieveUserById(int id){
		User user = null;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		String hql="from User u where u.uid like'%"+id+"%'";
		try {
			Query query=session.createQuery(hql);
			user=(User)query.list().get(0);
		} catch (HibernateException e) {
			System.out.println("DbUnit RetrieveUserById 出现异常：");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return user;
	}
        
	public static void main(String[] args){
		/*
		User user=new User();
		user.setKey( UUID.randomUUID().toString());
		user.setIp("127.0.0.1");
		user.setUid(1);
                user.setTimes(0);
		DbUnit.getDbUnit().CreateObject(user);*/
		/*
                ReptileLog reptileLog=new ReptileLog(1, "2017-01-15 16:53:00", "服务器启动");
		DbUnit.getDbUnit().CreateObject(reptileLog);
                */
              //  List<User> users=DbUnit.getDbUnit().RetrieveUser();
               // System.out.println(users.size());
               /**/
                User user=DbUnit.getDbUnit().RetrieveUserByIp("127.0.0.1");
                int times=user.getTimes();
                System.out.println(times);
                times++;
                user.setTimes(times);
               DbUnit.getDbUnit().UpdateObject(user);
                    
              // System.out.println(DbUnit.getDbUnit().RetrieveUserById(1).getKey());
	}
        
       
}
