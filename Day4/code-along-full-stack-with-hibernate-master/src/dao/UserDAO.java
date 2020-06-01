package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityTransaction;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.User;
import utility.ConnectionManager;
import utility.HibernateConnectionManager;

public class UserDAO implements UserDaoInterface {

	 private SessionFactory sessionFactory = HibernateConnectionManager.getSessionFactory();
	 
	 @Override
		public int signUp(User user) {
			
			    Session session = this.sessionFactory.openSession();
		        org.hibernate.Transaction transaction = session.beginTransaction();  
		        if(session.save(user)!=null)
		        	{
		        		 transaction.commit();
		        		 session.close();
		        		return 1;
		        	}
		        	else {
		        		 return 0;  
		        	}
		       
		}
	public boolean loginUser(User user) {
		
        Session session = this.sessionFactory.openSession();
	        Transaction tx = null;
	      
	        try {
	            tx =(Transaction) session.getTransaction();
	            ((EntityTransaction) tx).begin();
	            Query query = session.createQuery("from Users where email='"+user.getEmail()+"'"+"and password ='"+user.getPassword()+"'");
	            user = (User)query.uniqueResult();
	            tx.commit();
	        } catch (Exception e) {
	            if (tx != null) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return true;
	}

	
	
}
