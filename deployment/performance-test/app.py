from datetime import datetime

import pandas as pd
from locust import HttpUser, between, task

results_data = []
CSV_FILENAME = "results.csv"


class PostApiServiceUser(HttpUser):
    wait_time = between(1, 2)

    @task
    def check_send_notification(self):
        headers = {
            "Content-Type": "application/json",
            "Accept": "application/json",
        }

        payload = {"title": "Inversiones", "message": "Prueba notificación push Uno a UNO QA"}

        expected_title = "Titulo de la notificacion"
        expected_message = "Mensaje de la notificacion"

        with self.client.post(
            "/api/v1/sender/send", json=payload, headers=headers, name="/api/v1/sender/send", catch_response=True
        ) as response:
            try:
                if response.status_code != 200:
                    response.failure(f"Status code no fue 200, fue {response.status_code}")
                    return

                data = response.json()
                if data["title"] == expected_title and data["message"] == expected_message:
                    response.success()
                else:
                    response.failure(
                        f"Error: title or message do not match. Got title='{data['title']}', message='{data['message']}'"
                    )

            except (KeyError, IndexError, TypeError):
                response.failure({response.text})
            except Exception as e:
                response.failure(f"Exception occurred: {e}")


# @events.request.add_listener
def on_request(
    request_type, name, response_time, response_length, response, context, exception, start_time, url, **kwargs
):
    data = response.json().get("data", [])
    feature_name = data[0].get("name", "N/A")

    if exception:
        validation_status = "FALSE"
        error_message = str(exception)
    else:
        validation_status = "TRUE"
        error_message = ""

    results_data.append(
        {
            "timestamp": datetime.fromtimestamp(start_time).strftime("%Y-%m-%d %H:%M:%S"),
            "feature_name": feature_name,
            "endpoint": name,
            "method": request_type,
            "response_time_ms": int(response_time),
            "status": validation_status,
            "error": error_message,
        }
    )


# @events.test_stop.add_listener
def on_test_stop(environment, **kwargs):
    if not results_data:
        print("No se generaron datos, no se creará el archivo Excel.")
        return

    print(f"\nGenerando reporte en Excel... ({len(results_data)} registros)")
    df = pd.DataFrame(results_data)

    try:
        df.to_csv(CSV_FILENAME, index=False)
        print(f"Reporte guardado exitosamente en '{CSV_FILENAME}'")
    except Exception as e:
        print(f"Error al guardar el archivo CSV: {e}")
