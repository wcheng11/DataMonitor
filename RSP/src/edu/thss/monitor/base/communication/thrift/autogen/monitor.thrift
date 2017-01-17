namespace java edu.thss.monitor.base.communication.thrift.autogen   #基础框架/基础通信构件/thrift自动生成代码所在包名

service ThriftResource {	#Storm集群资源节点的Thrift服务
	binary thriftGetResource(1:string resourceType) 	#[资源同步]:获得资源
	void thriftReceiveChange(1:binary param)			#[资源同步]:接收更新
	binary thriftRegister(1:binary param)			    #[资源同步]:注册同步客户端
}

service ThriftProcess {		#Storm集群处理节点的Thrift服务
	void thriftReceiveChange(1:binary param)			#[资源同步]:接收更新
	void thriftSetLogLevel(1:string level)				#[平台管理]:设置日志级别
}

service ThriftPlatform {	#平台层的Thrift服务
	void thriftReceivePushData(1:binary param)			#接收推送数据
	void thriftReceiveLog(1:string source,2:string level,3:string content)	#接收日志信息：source(来源)、level(日志级别)、content(日志内容)
}