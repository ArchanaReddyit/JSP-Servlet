package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.User;
import utility.ConnectionManager;
import utility.HibernateConnectionManager;

public class UserDAO implements UserDaoInterface {

	 private SessionFactory sessionFactory = HibernateConnectionManager.getSessionFactory();
	
//	public int signUp(User user) {
//		String INSERT_USERS_SQL = "INSERT INTO USERS(email, password)VALUES(?,?)";
//
//		int result = 0;
//		try
//		{
//			Connection connection = ConnectionManager.getConnection();
//			// Step 2:Create a statement using connection object
//			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
//			preparedStatement.setString(1,user.getEmail());
//			preparedStatement.setString(2,user.getPassword());
//			System.out.println(preparedStatement);
//			// Step 3: Execute the query or update query
//			result = preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println(e);
//		}
//		return result;
//	}
	
//	public boolean loginUser(User user) {
//		boolean status = false;
//		try{
//			Connection connection = ConnectionManager.getConnection();
//		
//				// Step 2:Create a statement using connection object
//		PreparedStatement preparedStatement = connection.prepareStatement("select * from users where email = ? and password = ? ");
//		
//			preparedStatement.setString(1, user.getEmail());
//			preparedStatement.setString(2, user.getPassword());
//
//			System.out.println(preparedStatement);
//			ResultSet rs = preparedStatement.executeQuery();
//			status = rs.next();
//
//		} catch (SQLException e) {
//			// process sql exception
//			System.out.println(e);
//		}
//		return status;
//	}

	public boolean loginUser(User user) {
		
        Session session = this.sessionFactory.openSession();
	        Transaction tx = null;
	      
	        try {
	            tx = session.getTransaction();
	            tx.begin();
	            Query query = session.createQuery("from User where email='"+user.getEmail()+"'"+"and password ='"+user.getPassword()+"'");
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

	@Override
	public int signUp(User user) {
		
		    Session session = this.sessionFactory.openSession();
	        Transaction transaction = session.beginTransaction();  
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
	
}
