package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class OilLevel implements IDataTrans{

	//ClassisType为3，SensorType为3，JBC108TYPE为4
	@Override
	public String trans(String value) throws RSPException {
		// TODO Auto-generated method stub
		Unsign32Int unsign32Int = new Unsign32Int();
		String postValue = unsign32Int.trans(value);
		try {
			float y = CalcOilLevel(Float.parseFloat(postValue) / 1000.0f);
			Integer result = (int)(y * 300 * 1000);
			return result.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}

		
	}

	
	private float CalcOilLevel(float x) throws Exception{
		float y = 0;
		
		float OilLevel[][] = new float[32][2];
	    int OilLevelLen = 32;
	    OilLevel[0][0] = 2.344f;
	    OilLevel[0][1] = 525;

	    OilLevel[1][0] = 2.309f;
	    OilLevel[1][1] = 495;

	    OilLevel[2][0] = 2.273f;
	    OilLevel[2][1] = 480;

	    OilLevel[3][0] = 2.236f;
	    OilLevel[3][1] = 465;

	    OilLevel[4][0] = 2.198f;
	    OilLevel[4][1] = 450;

	    OilLevel[5][0] = 2.156f;
	    OilLevel[5][1] = 435;

	    OilLevel[6][0] = 2.113f;
	    OilLevel[6][1] = 420;

	    OilLevel[7][0] = 2.068f;
	    OilLevel[7][1] = 405;

	    OilLevel[8][0] = 2.022f;
	    OilLevel[8][1] = 395;

	    OilLevel[9][0] = 1.944f;
	    OilLevel[9][1] = 375;

	    OilLevel[10][0] = 1.883f;
	    OilLevel[10][1] = 360;

	    OilLevel[11][0] = 1.83f;
	    OilLevel[11][1] = 345;

	    OilLevel[12][0] = 1.775f;
	    OilLevel[12][1] = 330;

	    OilLevel[13][0] = 1.719f;
	    OilLevel[13][1] = 315;

	    OilLevel[14][0] = 1.661f;
	    OilLevel[14][1] = 300;

	    OilLevel[15][0] = 1.605f;
	    OilLevel[15][1] = 285;

	    OilLevel[16][0] = 1.542f;
	    OilLevel[16][1] = 270;

	    OilLevel[17][0] = 1.477f;
	    OilLevel[17][1] = 255;

	    OilLevel[18][0] = 1.409f;
	    OilLevel[18][1] = 240;

	    OilLevel[19][0] = 1.329f;
	    OilLevel[19][1] = 225;

	    OilLevel[20][0] = 1.255f;
	    OilLevel[20][1] = 210;

	    OilLevel[21][0] = 1.179f;
	    OilLevel[21][1] = 195;

	    OilLevel[22][0] = 1.099f;
	    OilLevel[22][1] = 180;

	    OilLevel[23][0] = 1.021f;
	    OilLevel[23][1] = 165;

	    OilLevel[24][0] = 0.941f;
	    OilLevel[24][1] = 150;

	    OilLevel[25][0] = 0.839f;
	    OilLevel[25][1] = 135;

	    OilLevel[26][0] = 0.732f;
	    OilLevel[26][1] = 120;

	    OilLevel[27][0] = 0.618f;
	    OilLevel[27][1] = 105;

	    OilLevel[28][0] = 0.499f;
	    OilLevel[28][1] = 90;

	    OilLevel[29][0] = 0.372f;
	    OilLevel[29][1] = 75;

	    OilLevel[30][0] = 0.238f;
	    OilLevel[30][1] = 60;

	    OilLevel[31][0] = 0.238f;
	    OilLevel[31][1] = 0;
	    
	    
	  //把两头的拿出来分别处理
	    if (x == OilLevel[0][0])
	    {
	        y = OilLevel[0][1];
	    }
	    else if (x > OilLevel[0][0])
	    {
	        throw new Exception("油位解析异常");//y = OilLevel[0][1];
	    }

	    if(x < OilLevel[OilLevelLen - 1][0])
	    {
	    	throw new Exception("油位解析异常");//y = OilLevel[OilLevelLen - 1][1];
	    }

	    //中间部分
	    for (int i = 1; i < OilLevelLen - 1; i++)
	    {
	        if ((x < OilLevel[i-1][0]) && (x >= OilLevel[i][0]))
	        {
	            //根据直线斜率计算
	            y = ((OilLevel[i-1][1] - OilLevel[i][1])/(OilLevel[i-1][0] - OilLevel[i][0]))*(x-OilLevel[i][0])+OilLevel[i][1];
	            break;
	        }
	    }

	    y = y / 525;

	    return y;
	}
	
//	private float CalcOilLevel(int x, int iXinghao) throws Exception{
//		float y = 0;
//		
//		int   x1=0,x2=0,x3=0,x4=0,x5=0;
//	    float y1=0,y2=0,y3=0,y4=0,y5=0;
//
//	    //根据油位传感器来计算油位, add by zoul2
//	    switch (iSensorType)
//	    {
//	    case 0:break;//未知
//	    case 1:break;//“YT305D-SY”
//	    case 2:break;//“YT305C-SY-550(单) ”
//	    case 3://“YT305C-SY-550(双) ”
//	        return CalcOilLevelBySensor(x/1000.0f);
//	    }
//	    
//	    switch(iXinghao)
//	    {
//	    case 1:
//	        {
//	            //得到参数X1
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_X1");
//	            //if(pPV == NULL) return false;
//	            x1 = 148;
//	            //得到参数Y1
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_Y1");
//	            //if(pPV == NULL) return false;
//	            y1 = 0.0f;
//	            //计算
//	            if(x < x1)
//	            {
//	                throw new Exception("油位解析异常");
//	            }
//	            else if(x == x1)
//	            {
//	                y = y1;
//	                return y;
//	            }
//	            
//	            //得到参数X2
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_X2");
//	            //if(pPV == NULL) return false;
//	            x2 = 678;
//	            //得到参数Y2
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_Y2");
//	            //if(pPV == NULL) return false;
//	            y2 = 0.375f;
//	            //计算
//	            if(x <= x2)
//	            {
//	                y = ((y2-y1)*x+x2*y1-x1*y2)/(x2-x1);
//	                return y;
//	            }
//	            
//	            
//	            //得到参数X3
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_X3");
//	            //if(pPV == NULL) return false;
//	            x3 = 942;
//	            //得到参数Y3
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_Y3");
//	            //if(pPV == NULL) return false;
//	            y3 = 0.5f;
//	            //计算
//	            if(x <= x3)
//	            {
//	                y = ((y3-y2)*x+x3*y2-x2*y3)/(x3-x2);
//	                return y;
//	            }
//	            
//	            //得到参数X4
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_X4");
//	            //if(pPV == NULL) return false;
//	            x4 = 1235;
//	            //得到参数Y4
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_Y4");
//	            //if(pPV == NULL) return false;
//	            y4 = 0.75f;
//	            //计算
//	            if(x <= x4)
//	            {
//	                y = ((y4-y3)*x+x4*y3-x3*y4)/(x4-x3);
//	                return y;
//	            }
//	            
//	            //得到参数X5
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_X5");
//	            //if(pPV == NULL) return false;
//	            x5 = 1535;
//	            //得到参数Y5
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,1,"jbc_OilLevel_Y5");
//	            //if(pPV == NULL) return false;
//	            y5 = 1.0f;
//	            //计算
//	            if(x <= x5)
//	            {
//	                y = ((y5-y4)*x+x5*y4-x4*y5)/(x5-x4);
//	                return y;
//	            }
//	            else
//	            {
//	            	throw new Exception("油位解析异常");
//	            }
//	        }
//	    case 2:
//	        {
//	            //得到参数X1
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_X1");
//	            //if(pPV == NULL) return false;
//	            x1 = 250;
//	            //得到参数Y1
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_Y1");
//	            //if(pPV == NULL) return false;
//	            y1 = 1.0f;
//	            //计算
//	            if(x < x1)
//	            {
//	            	throw new Exception("油位解析异常");
//	            }
//	            else if(x == x1)
//	            {
//	                y = y1;
//	                return y;
//	            }
//	            
//	            //得到参数X2
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_X2");
//	            //if(pPV == NULL) return false;
//	            x2 = 2700;
//	            //得到参数Y2
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_Y2");
//	            //if(pPV == NULL) return false;
//	            y2 = 0.75f;
//	            //计算
//	            if(x <= x2)
//	            {
//	                y = ((y2-y1)*x+x2*y1-x1*y2)/(x2-x1);
//	                return y;
//	            }
//	            
//	            
//	            //得到参数X3
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_X3");
//	            //if(pPV == NULL) return false;
//	            x3 = 5700;
//	            //得到参数Y3
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_Y3");
//	            //if(pPV == NULL) return false;
//	            y3 = 0.5f;
//	            //计算
//	            if(x <= x3)
//	            {
//	                y = ((y3-y2)*x+x3*y2-x2*y3)/(x3-x2);
//	                return y;
//	            }
//	            
//	            //得到参数X4
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_X4");
//	            //if(pPV == NULL) return false;
//	            x4 = 7400;
//	            //得到参数Y4
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_Y4");
//	            //if(pPV == NULL) return false;
//	            y4 = 0.25f;
//	            //计算
//	            if(x <= x4)
//	            {
//	                y = ((y4-y3)*x+x4*y3-x3*y4)/(x4-x3);
//	                return y;
//	            }
//	            
//	            //得到参数X5
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_X5");
//	            //if(pPV == NULL) return false;
//	            x5 = 9050;
//	            //得到参数Y5
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,2,"jbc_OilLevel_2_Y5");
//	            //if(pPV == NULL) return false;
//	            y5 = 0.0f;
//	            //计算
//	            if(x <= x5)
//	            {
//	                y = ((y5-y4)*x+x5*y4-x4*y5)/(x5-x4);
//	                return y;
//	            }
//	            else
//	            {
//	            	throw new Exception("油位解析异常");
//	            }
//	        }
//	    case 3:
//	        {
//	            //得到参数X1
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,3,"jbc_OilLevel_3_X1");
//	            //if(pPV == NULL) return false;
//	            x1 = 1300;
//	            //得到参数Y1
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,3,"jbc_OilLevel_3_Y1");
//	            //if(pPV == NULL) return false;
//	            y1 = 0.0f;
//	            //计算
//	            if(x < x1)
//	            {
//	            	throw new Exception("油位解析异常");
//	            }
//	            else if(x == x1)
//	            {
//	                y = y1;
//	                return y;
//	            }
//	            
//	            //得到参数X2
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,3,"jbc_OilLevel_3_X2");
//	            //if(pPV == NULL) return false;
//	            x2 = 3000;
//	            //得到参数Y2
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,3,"jbc_OilLevel_3_Y2");
//	            //if(pPV == NULL) return false;
//	            y2 = 0.2381f;
//	            //计算
//	            if(x <= x2)
//	            {
//	                y = ((y2-y1)*x+x2*y1-x1*y2)/(x2-x1);
//	                return y;
//	            }
//	            
//	            //得到参数X3
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,3,"jbc_OilLevel_3_X3");
//	            //if(pPV == NULL) return false;
//	            x3 = 7230;
//	            //得到参数Y3
//	            //pPV = theApp.m_MemData.ReadMeaParamVal(7,3,"jbc_OilLevel_3_Y3");
//	            //if(pPV == NULL) return false;
//	            y3 = 1.0f;
//	            //计算
//	            if(x <= x3)
//	            {
//	                y = ((y3-y2)*x+x3*y2-x2*y3)/(x3-x2);
//	                return y;
//	            }
//	            else
//	            {
//	            	throw new Exception("油位解析异常");
//	            }
//	        }
//	    case 4:break;
//	    default:break;
//	    }
//	    throw new Exception("油位解析异常");
//	}
}
