<?php
class BookletsController extends AppController {
	var $name = 'Booklets';
	
	/**
	 * List all the booklets data menus.
	 */
	public function listMenus() {
		$this->set('booklets', $this->Booklet->query("SELECT * FROM page_datas WHERE page_datas.id = booklets.page_data_id"));
        $this->set('_serialize', 'booklets');
    }
    
    /**
	  * Get the data associated with the given booklet (page_data).
	  */
	public function getData($bookletId) {
		$this->set('bookletData', $this->Booklet->PageData->find('all',
        	array(
				'conditions' => array(
					'Booklet.id' => $bookletId),
//				'recursive' => 1, //int
//				'fields' => array('Model.field1', 'DISTINCT Model.field2'), //array of field names
//				'order' => array('Model.created', 'Model.field3 DESC'), //string or array defining order
//				'group' => array('Model.field'), //fields to GROUP BY
//				'limit' => n, //int
//				'page' => n, //int
//				'offset' => n, //int
//				'callbacks' => true //other possible values are false, 'before', 'after'
			)));
        $this->set('_serialize', 'bookletData');
	}
}
?>
