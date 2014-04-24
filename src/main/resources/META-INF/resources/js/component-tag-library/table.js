+function($) {
	'use strict';
	var Table = function(element, settings, ajaxOptions) {
		this.settings = $.extend(true, {}, Table.settings, settings);
		this.ajaxOption = $.extend(true, {}, Table.ajaxOptions, ajaxOptions);
		this.$element = $(element);
		this.$element.on(this.getNamespacedEvent('content-load'), $.proxy(this.replace, this));
	};

	Table.ajaxOptions = {
		url : window.location.pathname
	};

	Table.settings = {
		namespace : 'component-tag-library.table'
	};

	Table.prototype.update = function(options) {
		var data = $.extend(true, {}, this.ajaxOptions, options);
		var xhr = $.ajax(data);
		xhr.done($.proxy(function(response) {
			this.$element.trigger(this.getNamespacedEvent('content-load'), [ $.parseHTML(response) ]);
		}, this));
		xhr.fail($.proxy(function(jqXhr, textStatus, error) {
			this.$element.trigger(this.getNamespacedEvent('content-load-fail'), [ jqXhr, textStatus, error ]);
		}, this));
	};

	Table.prototype.replace = function(event, response) {
		var $response = $(response);
		var $newContent = $response.find('table[data-jsp-id="' + this.$element.data('jsp-id') + '"]');
		this.$element.html($newContent.html());
	};
	
	Table.prototype.getNamespacedEvent = function(event) {
		return event + '.' + this.settings.namespace;
	};

	$.fn.table = function(settings, ajaxOptions) {
		return this.each(function() {
			var $this = $(this);
			var data = $this.data('comp.table');
			if (!data) {
				data = new Table(this, settings, ajaxOptions);
				$this.data('comp.table', data);
			}
			if (settings == 'update') {
				data[settings](ajaxOptions);
			}
		});
	};

	$.fn.table.Constructor = Table;

	$(document).on('ready', function() {
		$('table[data-jsp-id]').each(function(index, element) {
			$(element).table();
		});
	});

}(jQuery);