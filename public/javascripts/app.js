jQuery.fn.clickButtonOnEnter = function(buttonId) {
    var me = jQuery(this[0]) // The bound element
    
	jQuery(me).keypress(function(e) {
		if (e.keyCode == 13) {
			jQuery('#' + buttonId).click();
		}
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