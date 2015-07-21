package DB;

import base.ObjectORM;

public class SectionORM extends ObjectORM {
	
	private static SectionORM instance;
	
	public synchronized static SectionORM getInstance(){
		if(instance==null) instance=new SectionORM();
		return instance;
	}
	
	public Section loadSection(int sectionN,String user_id){		
		Section sec = new Section();
		
		TaskTemplate t = TaskTemplateORM.getInstance().loadById(sectionN);
		String[] prb_list = t.getPrb_list().split(";");	
		String[] prb_name_list = new String[prb_list.length];
		
		for(int i=0;i<prb_list.length;i++){
			Problem p = 
				ProblemORM.getInstance().LoadDetailByPrbId(Integer.parseInt(prb_list[i]));
			prb_name_list[i] = p.getTitle();
		}
		
		sec.setPrb_id_list(prb_list);
		sec.setPrb_name_list(prb_name_list);
				
		int status = UserDoTaskORM.getInstance().loadStatus(0, user_id);
		status = status >> (sectionN*4);	
		sec.setStatus(status & 15);
	
		return sec;
	}
}
