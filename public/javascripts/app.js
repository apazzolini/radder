var RADDER = function($) { 
	/* PRIVATE */
	function getResults(userid, $tab) {
		var $activeTab = $resultsTabs.find('a.active');
		
		var resultsUrl = '/results';
		if (userid != null) {
			resultsUrl += '/' + userid;
		}
		
		$activeTab.removeClass('active');
		$tab.addClass('active');
		
		$.get(resultsUrl, {ts: new Date()}, function(data) {
			$('#resultsTabContent').html(data);
		});
	}

	/* PUBLIC */
	function userResults(userid) {
		getResults(userid, $resultsTabs.find('#userResultsTab'));
	}
	
	function myResults(userid) {
		getResults(userid, $resultsTabs.find('#myResultsTab'));
	}
	
	function allResults() {
		getResults(null, $resultsTabs.find('#allResultsTab'));
	}

	function challengeUser(userid) {
		$.post('/challenge', {userid: userid}, function(data) {
			window.location.reload();
		});
	}

	function approveResult(gameId) {
		$.post('/approveResult', {gameId: gameId}, function(data) {
			window.location.reload();
		});
	}
	
	function rejectResult(gameId) {
		$.post('/rejectResult', {gameId: gameId}, function(data) {
			window.location.reload();
		});
	}
	
	function logResult(gameId, oneScore, twoScore) {
		$.post('/logResult', {gameId: gameId, oneScore: oneScore, twoScore: twoScore}, function(data) {
			if (data == 'OK' || data == 'REFRESH') { 
				window.location.reload();
			} else {
				$('#challengesMessage').html(data).addClass('alert-box error');
			}
		});
	}
	
	function postComment(comment) {
		$.post('/comment', {comment: comment}, function(data) {
			if (data == 'OK') { 
				window.location.reload();
			} else {
				$('#commentMessage').html(data).addClass('alert-box error');
			}
		});
		return false;
	}
	
	function postGameComment(gameId, comment) {
		$.post('/comment/' + gameId, {comment: comment}, function(data) {
			alert(data);
		});
	}
	
	return {
		userResults: userResults,
		myResults: myResults,
		allResults: allResults,
		challengeUser: challengeUser,
		approveResult: approveResult,
		rejectResult: rejectResult,
		logResult: logResult,
		postComment: postComment,
		postGameComment: postGameComment
	}
}(jQuery);