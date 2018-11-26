INSERT  INTO
	cvc_delivery_goods
      ( 
      delivery_id                    
      ,goods_id                       
      ,product_id                     
      ,product_sn                     
      ,goods_name                     
      ,brand_name                     
      ,goods_sn                       
      ,is_real                        
      ,extension_code                 
      ,parent_id                      
      ,send_number                    
      ,goods_attr                     
      ,system_sku                     
      ,goods_price                    
      ,upc_id                         
      ) 
values
      (
      :cvcDeliveryGoods.deliveryId                    
      ,:cvcDeliveryGoods.goodsId                       
      ,:cvcDeliveryGoods.productId                     
      ,:cvcDeliveryGoods.productSn                     
      ,:cvcDeliveryGoods.goodsName                     
      ,:cvcDeliveryGoods.brandName                     
      ,:cvcDeliveryGoods.goodsSn                       
      ,:cvcDeliveryGoods.isReal                        
      ,:cvcDeliveryGoods.extensionCode                 
      ,:cvcDeliveryGoods.parentId                      
      ,:cvcDeliveryGoods.sendNumber                    
      ,:cvcDeliveryGoods.goodsAttr                     
      ,:cvcDeliveryGoods.systemSku                     
      ,:cvcDeliveryGoods.goodsPrice                    
      ,:cvcDeliveryGoods.upcId                         
      )