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

//�������ݿ⹤����
public class DbUnit {
	
	private static DbUnit dbUnit;
	private static SessionFactory sessionFactory;
	
	private DbUnit(){
		Configuration configuration=new Configuration().configure();//�������ö���
		ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();//��������ע�����
		sessionFactory=configuration.buildSessionFactory(serviceRegistry);//�����Ự������������
	}
	
	public static DbUnit getDbUnit(){
		if(dbUnit==null){
			dbUnit=new DbUnit();
		}
		return dbUnit;
	}
        
	
	/**�����ݱ��CUD
	CRUD��ָ�������㴦��ʱ������(Create)������(Update)��ɾ��(Delete)�������ʵ�����ĸ��д
	*/
	
	/**
	 * ���ݿ�������ݲ���
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
			System.out.println("DbUnit CreateObject �����쳣��"+e.toString());
			result=false;
		}finally {
			session.close();
		}
		return result;
	}
	
	/**
	 * ���ݿ�ɾ�����ݲ���
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
			System.out.println("DbUnit DeleteObject �����쳣��"+e.getMessage());
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
			System.out.println("DbUnit UpdateObject �����쳣��");
			e.printStackTrace();
			result=false;
		}finally {
			session.close();
		}
		return result;
	}
	
	/**�����ݱ�Ķ�ȡ��ѯ(Retrieve)
	*/
	
	/**
	 * ����key��User���ѯ����
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
			System.out.println("DbUnit RetrieveUserByKey �����쳣��");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return user;
	}
	
	/**
	 * ����uniquekey��SinaArti���ѯ����
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
			System.out.println("DbUnit RetrieveSinaArtiByUniquekey �����쳣��");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return sinaArtis;
	}
	
	/**
	 * ����title��SinaArti���ѯ����
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
			System.out.println("DbUnit RetrieveSinaArtiByTitle �����쳣��");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return sinaArti;
	}
	
	/**
	 * ͳ�����ݿ��е�����
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
	 * ����������SinaArti��ѯ
	 * @param max ����ѯmax������
	 * @param first ��first�����ݿ�ʼ��
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
	 * ����ip��User���ѯ����
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
			System.out.println("DbUnit RetrieveUserByIp �����쳣��");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return user;
	}
        
         /**
	 * ��User�����user
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
			System.out.println("DbUnit RetrieveUser �����쳣��");
			e.printStackTrace();
		}finally {
			session.close();
		}
		return users;
	}
        
         /**
	 * ����id��User���ѯ����
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
			System.out.println("DbUnit RetrieveUserById �����쳣��");
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
                ReptileLog reptileLog=new ReptileLog(1, "2017-01-15 16:53:00", "����������");
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
