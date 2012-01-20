jQuery.fn.clickButtonOnEnter = function(buttonId) {
    var me = jQuery(this[0]) // The bound element
    
	jQuery(me).keypress(function(e) {
		if (e.keyCode == 13) {
			jQuery('#' + buttonId).click();
		}
	});
};

jQuery.fn.watermark = function(watermarkString) {
    var original = jQuery(this[0]) // The bound element
	var cloned = jQuery(original).clone();
    var form = jQuery(original).closest('form');
    
	jQuery(cloned).attr('id', jQuery(cloned).attr('id') + "_cloned");
	jQuery(cloned).attr('type', 'text');
	jQuery(original).after(jQuery(cloned));
	
	if (jQuery(original).val().length == 0) {
		jQuery(original).hide();
		jQuery(original).attr('disabled', true);
	} else {
		jQuery(cloned).hide();
		jQuery(cloned).attr('disabled', true);
	}
	
	jQuery(cloned).css('color', '#999999');
	jQuery(cloned).val(watermarkString);
	
	jQuery(cloned).focus(function() {
		jQuery(this).hide();  
		jQuery(this).attr('disabled', true);
	 	jQuery(original).attr('disabled', false);
	 	jQuery(original).show();
	  	jQuery(original).focus();
	});

	jQuery(original).blur(function() {
	    if (jQuery(this).val().length == 0) {
	       	jQuery(this).hide();  
	       	jQuery(this).attr('disabled', true);
	       	jQuery(cloned).show();
	       	jQuery(cloned).attr('disabled', false);
	    } else {
	    	jQuery(cloned).attr('disabled', true);
	    }
	});
	
    jQuery(form).submit(function() {
    	jQuery(cloned).attr('disabled', true);
    	jQuery(original).attr('disabled', false);
    });
};

function userResults(userid) {
	var $activeTab = $resultsTabs.find('a.active'),
	 	$tab       = $resultsTabs.find('#userResultsTab');
	resultsInternal(userid, $activeTab, $tab);
}

function myResults(userid) {
	var $activeTab = $resultsTabs.find('a.active'),
	 	$tab       = $resultsTabs.find('#myResultsTab');
	resultsInternal(userid, $activeTab, $tab);
}

function allResults() {
	var $activeTab = $resultsTabs.find('a.active'),
	 	$tab       = $resultsTabs.find('#allResultsTab');
	resultsInternal(null, $activeTab, $tab);
}

function resultsInternal(userid, $activeTab, $tab) {
	var resultsUrl = '/results';
	if (userid != null) {
		resultsUrl += '/' + userid;
	}
	
	$activeTab.removeClass('active');
	$tab.addClass('active');
	
	$.get(resultsUrl, {
		ts: new Date() 
		}, function(data) {
			$('#resultsTabContent').html(data);
		}
	);
}

function challengeUser(userid) {
	$.post('/challenge', {
		userid   : userid
		}, function(data) {
			window.location.reload();
		}
	);
}

function logResult(gameId, oneScore, twoScore) {
	$.post('/logResult', {
		gameId   : gameId,
		oneScore : oneScore,
		twoScore : twoScore
		}, function(data) {
			if (data == 'OK') { 
				window.location.reload();
			} else {
				$('#challengesMessage').html(data).addClass('alert-box error');
			}
		}
	);
}

function postComment(comment) {
	$.post('/comment', {
		comment   : comment
		}, function(data) {
			if (data == 'OK') { 
				window.location.reload();
			} else {
				$('#commentMessage').html(data).addClass('alert-box error');
			}
		}
	);
}

function postGameComment(gameId, comment) {
	$.post('/comment/' + gameId, {
		comment   : comment
		}, function(data) {
			alert(data);
		}
	);
}