INSERT  INTO
	cvc_order_action
      ( 
      action_id                     
      ,order_id                       
      ,action_user                    
      ,order_status                   
      ,shipping_status                
      ,pay_status                     
      ,action_place                   
      ,action_note                    
      ,log_time                       
      ) 
values
      (
      :cvcOrderAction.actionId                      
      ,:cvcOrderAction.orderId                       
      ,:cvcOrderAction.actionUser                    
      ,:cvcOrderAction.orderStatus                   
      ,:cvcOrderAction.shippingStatus                
      ,:cvcOrderAction.payStatus                     
      ,:cvcOrderAction.actionPlace                   
      ,:cvcOrderAction.actionNote                    
      ,:cvcOrderAction.logTime                       
      )