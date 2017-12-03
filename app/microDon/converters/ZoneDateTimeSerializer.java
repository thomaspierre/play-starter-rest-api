package microDon.converters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZoneDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

	@Override
	public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(FORMATTER.format(value));
	}

}
