<?php
class Menu extends AppModel {
	public $name = 'Menu';
	
	public $belongsTo = array(
    	'PageData' => array(
        	'className'    => 'PageData',
        	'dependent'    => true
		)
    );
}
?>
