package microDon.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.core.JsonToken.VALUE_STRING;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

	@Override
	public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		if(VALUE_STRING == parser.getCurrentToken()) {

				String rawDate = parser.getText();
				return FORMATTER.parse(rawDate, LocalDate::from);
		}

		throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected string.");
	}
}
