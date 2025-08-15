package com.shopmanagement.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Global Exception Handler
 * Xử lý các exception trong toàn bộ application
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Xử lý RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        logger.error("Runtime Exception occurred: ", ex);
        
        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("requestUrl", request.getRequestURL().toString());
        
        return modelAndView;
    }
    
    /**
     * Xử lý IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        logger.warn("Illegal Argument Exception: ", ex);
        
        ModelAndView modelAndView = new ModelAndView("error/400");
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("requestUrl", request.getRequestURL().toString());
        
        return modelAndView;
    }
    
    /**
     * Xử lý các exception chung
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error occurred: ", ex);
        
        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("errorMessage", "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau.");
        modelAndView.addObject("requestUrl", request.getRequestURL().toString());
        
        return modelAndView;
    }
    
    /**
     * Xử lý lỗi database
     */
    @ExceptionHandler({
        org.springframework.dao.DataAccessException.class,
        javax.persistence.PersistenceException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleDatabaseException(Exception ex, HttpServletRequest request) {
        logger.error("Database error occurred: ", ex);
        
        ModelAndView modelAndView = new ModelAndView("error/database");
        modelAndView.addObject("errorMessage", "Lỗi kết nối cơ sở dữ liệu. Vui lòng thử lại sau.");
        modelAndView.addObject("requestUrl", request.getRequestURL().toString());
        
        return modelAndView;
    }
}
