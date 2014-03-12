+function($) {
	'use strict';

	var Table = function(element, options) {
		this.$element = $(element);
		this.$element.on('content-load', $.proxy(this.replace, this));
	}

	Table.prototype.update = function(options) {
		var defaults = {
			url : window.location.pathname
		};
		$.extend(defaults, options);
		var xhr = $.ajax(defaults);
		xhr.done($.proxy(function(response) {
			this.$element.trigger('content-load', [ $.parseHTML(response) ]);
		}, this));
		xhr.fail($.proxy(function(jqXhr, textStatus, error) {
			this.$element.trigger('content-load-fail', [ jqXhr, textStatus, error ]);
		}, this));
	}

	Table.prototype.replace = function(event, response) {
		var $response = $(response);
		var $newContent = $response.find('table[data-jsp-id="' + this.$element.data('jsp-id') + '"]');
		this.$element.html($newContent.html());
	}

	$.fn.table = function(option) {
		return this.each(function() {
			var $this = $(this);
			var data = $this.data('comp.table');
			if (!data) {
				data = new Table(this, null);
				$this.data('comp.table', data);
			}
			if (typeof option == 'string') {
				data[option]();
			}
		});
	}

	$.fn.table.Constructor = Table;

	$(window).on('load', function() {
		$('table[data-jsp-id]').each(function(index, element) {
			$(element).table();
		});
	})

}(jQuery);