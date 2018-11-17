INSERT  INTO
	cvc_revoke_goods
      ( 
      r_id                          
      ,revk_id                        
      ,revk_sn                        
      ,order_id                       
      ,order_sn                       
      ,goods_name                     
      ,goods_sn                       
      ,system_sku                     
      ,color_id                       
      ,color_name                     
      ,size_id                        
      ,size_name                      
      ,market_price                   
      ,goods_price                    
      ,number                         
      ,upc_id                         
      ,img_path                       
      ) 
values
      (
      :cvcRevokeGoods.rId                           
      ,:cvcRevokeGoods.revkId                        
      ,:cvcRevokeGoods.revkSn                        
      ,:cvcRevokeGoods.orderId                       
      ,:cvcRevokeGoods.orderSn                       
      ,:cvcRevokeGoods.goodsName                     
      ,:cvcRevokeGoods.goodsSn                       
      ,:cvcRevokeGoods.systemSku                     
      ,:cvcRevokeGoods.colorId                       
      ,:cvcRevokeGoods.colorName                     
      ,:cvcRevokeGoods.sizeId                        
      ,:cvcRevokeGoods.sizeName                      
      ,:cvcRevokeGoods.marketPrice                   
      ,:cvcRevokeGoods.goodsPrice                    
      ,:cvcRevokeGoods.number                        
      ,:cvcRevokeGoods.upcId                         
      ,:cvcRevokeGoods.imgPath                       
      )